"""Run with python3; requires a JDK 8, selected via JDK8 or macOS java_home."""
from pathlib import Path
import os
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parent
jdk = os.environ.get('JDK8')
if not jdk:
    jdk = subprocess.check_output(['/usr/libexec/java_home', '-v', '1.8'], text=True).strip()
BIN = Path(jdk) / 'bin'
version = subprocess.run([str(BIN / 'java'), '-version'], capture_output=True, text=True, check=True)
assert '1.8.' in version.stderr, version.stderr

def compile_to(out, *sources):
    return subprocess.run([str(BIN / 'javac'), '-J-Duser.language=en', '-encoding',
                           'UTF-8', '-d', str(out), *map(str, sources)],
                          cwd=ROOT, capture_output=True, text=True)

def run(out, name):
    return subprocess.run([str(BIN / 'java'), '-cp', str(out), name],
                          cwd=ROOT, capture_output=True, text=True)

with tempfile.TemporaryDirectory(prefix='java-0-3-') as temp:
    out = Path(temp)
    compiled = compile_to(out, ROOT / 'OrderStats.java', ROOT / 'SyntaxChecks.java')
    assert compiled.returncode == 0, compiled.stderr
    for name in ['OrderStats', 'SyntaxChecks']:
        result = run(out, name)
        assert result.returncode == 0, result.stderr
        assert result.stdout == (ROOT / (name + '.out')).read_text(), result.stdout
        print(name + ': output matches')
    errors = {'Unassigned': 'might not have been initialized',
              'Condition': 'int cannot be converted to boolean',
              'MissingReturn': 'missing return statement',
              'ReturnOnly': 'already defined'}
    for name, diagnostic in errors.items():
        result = compile_to(out, ROOT / 'fail' / (name + '.java'))
        assert result.returncode != 0 and diagnostic in result.stderr, result.stderr
        print(name + ': expected compile error')
    result = compile_to(out, ROOT / 'fail' / 'Bounds.java')
    assert result.returncode == 0, result.stderr
    result = run(out, 'Bounds')
    assert result.returncode != 0 and 'ArrayIndexOutOfBoundsException: 3' in result.stderr
    print('Bounds: expected runtime exception')
    # Exercise: generate the changed source outside the repository.
    variant = out / 'OrderStats.java'
    variant.write_text((ROOT / 'OrderStats.java').read_text().replace('break;', 'continue;'))
    result = compile_to(out, variant)
    assert result.returncode == 0, result.stderr
    result = run(out, 'OrderStats')
    assert result.returncode == 0 and result.stdout == '件数=11\n可发货\n', result.stdout
    print('Exercise: total = 11')
    # Check the empty input and zero-length array claims independently.
    edge = out / 'EdgeCases.java'
    edge.write_text('''class EdgeCases {
        public static void main(String[] args) {
            System.out.println(OrderStats.sumBeforeStop(new int[0]));
            int[] empty = new int[0];
            System.out.println(empty.length);
            System.out.println(empty[0]);
        }
    }''')
    result = compile_to(out, ROOT / 'OrderStats.java', edge)
    assert result.returncode == 0, result.stderr
    result = run(out, 'EdgeCases')
    assert result.stdout == '0\n0\n' and result.returncode != 0
    assert 'ArrayIndexOutOfBoundsException: 0' in result.stderr
    print('Empty array: length 0, sum 0, index 0 rejected')
print('All JDK 8 checks passed.')
