"""Compile the independent Java 8 String examples and preserve actual output."""
import argparse
import json
from pathlib import Path
import subprocess
import tempfile


EXPECTED_OUTPUT = {
    "ComparePuzzle": "a==b=true\na==c=false\na.equals(c)=true\nfold==a=true\n",
    "PoolLab": (
        "a==b=true\na==c=false\na==d=false\na==e=true\nc==d=false\nd==e=false\n"
        "a.equals(d)=true\nc.intern()==a=true\nfresh.intern()==fresh=true\n"
    ),
    "FoldLab": "r1==a=true\nr2==a=false\nr3==a=true\n",
    "ImmutableLab": (
        "s=[ Notice ]\nupper=[ NOTICE ]\ntrimmed=[Notice]\n"
        "upper==s=false\ntrimmed==s=false\nclean.trim()==clean=true\n"
        "clean.substring(0)==clean=true\nshout.toUpperCase()==shout=true\n"
        "clean.replace('x','y')==clean=true\n"
    ),
    "HashLab": (
        "hashBefore=0\nhashCode=-1039690024\nhashCodeAgain=-1039690024\n"
        "hashAfter=-1039690024\n"
    ),
}
# ConcatCost 的毫秒数随机器与运行次数变化，只校验与耗时无关的两行。
CONCAT_FIXED_PREFIX = "sameContent=true\nlength=20000\n"
JAVAP_CASES = ("FoldLab", "ConcatCost")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--jdk", type=Path, help="JDK 8 home directory")
    args = parser.parse_args()
    jdk = args.jdk or Path(subprocess.check_output(
        ["/usr/libexec/java_home", "-v", "1.8"], text=True).strip())
    version = subprocess.run([str(jdk / "bin/java"), "-version"],
                             capture_output=True, text=True, check=True).stderr
    if 'version "1.8.' not in version:
        raise SystemExit("These examples require JDK 8.")
    root = Path(__file__).resolve().parent
    records = {"jdk": version, "cases": {}}
    with tempfile.TemporaryDirectory(prefix="java-1-3-") as output:
        for source in sorted(root.glob("*.java")):
            compiled = subprocess.run(
                [str(jdk / "bin/javac"), "-J-Duser.language=en", "-J-Duser.country=US",
                 "-encoding", "UTF-8", "-d", output, source.name],
                cwd=root, text=True, capture_output=True)
            if compiled.returncode != 0:
                raise SystemExit(f"Compilation failed: {source.name}\n{compiled.stderr}")
            run = subprocess.run(
                [str(jdk / "bin/java"), "-Dfile.encoding=UTF-8", "-cp", output,
                 source.stem], cwd=root, text=True, capture_output=True)
            if source.stem == "ConcatCost":
                ok = run.stdout.startswith(CONCAT_FIXED_PREFIX)
            else:
                ok = run.stdout == EXPECTED_OUTPUT[source.stem]
            if run.returncode != 0 or run.stderr or not ok:
                raise SystemExit(
                    f"Unexpected result: {source.stem} (exit {run.returncode})\n"
                    f"stdout:\n{run.stdout}\nstderr:\n{run.stderr}")
            records["cases"][source.stem] = {
                "source": source.name, "run_exit": run.returncode,
                "run_stdout": run.stdout, "run_stderr": run.stderr}
            if source.stem in JAVAP_CASES:
                javap = subprocess.run(
                    [str(jdk / "bin/javap"), "-c", "-cp", output, source.stem],
                    cwd=root, text=True, capture_output=True, check=True)
                records["cases"][source.stem]["javap_c"] = javap.stdout
                javap_dir = root / "javap"
                javap_dir.mkdir(exist_ok=True)
                (javap_dir / f"{source.stem}.txt").write_text(javap.stdout, encoding="utf-8")
    (root / "results.json").write_text(
        json.dumps(records, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    print(version.strip())
    for key, result in records["cases"].items():
        print(f"\n{key} (exit {result['run_exit']})")
        print(result["run_stdout"], end="")


if __name__ == "__main__":
    main()