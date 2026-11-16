"""Re-run the Java 8 experiments and record their unmodified output."""
import argparse
import json
from pathlib import Path
import subprocess
import tempfile


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--jdk", type=Path, help="JDK 8 home directory")
    args = parser.parse_args()
    jdk = args.jdk or Path(subprocess.check_output(
        ["/usr/libexec/java_home", "-v", "1.8"], text=True).strip())
    root = Path(__file__).resolve().parent
    version = subprocess.run([str(jdk / "bin/java"), "-version"],
                             text=True, capture_output=True, check=True).stderr
    if 'version "1.8.' not in version:
        raise SystemExit("These experiments require JDK 8.")
    results = {"jdk": version, "cases": {}}
    with tempfile.TemporaryDirectory(prefix="java-0-3-") as output:
        sources = (sorted(root.glob("*.java"))
                   + sorted((root / "errors").glob("*.java"))
                   + sorted(root.glob("break*/*.java")))
        for source in sources:
            relative = source.relative_to(root).as_posix()
            compile_result = subprocess.run(
                [str(jdk / "bin/javac"), "-J-Duser.language=en", "-J-Duser.country=US",
                 "-encoding", "UTF-8", "-d", output, source.name],
                cwd=source.parent, text=True, capture_output=True)
            expected_error = source.parent.name == "errors"
            if (compile_result.returncode != 0) != expected_error:
                raise SystemExit(f"Unexpected compilation result: {relative}\n{compile_result.stderr}")
            result = {"compile_exit": compile_result.returncode,
                      "compile_stdout": compile_result.stdout,
                      "compile_stderr": compile_result.stderr}
            if not expected_error:
                run_result = subprocess.run(
                    [str(jdk / "bin/java"), "-Dfile.encoding=UTF-8", "-cp", output, source.stem],
                    cwd=root, text=True, capture_output=True, check=True)
                result.update(run_stdout=run_result.stdout, run_stderr=run_result.stderr)
            results["cases"][relative] = result
    (root / "results.json").write_text(
        json.dumps(results, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    print(version.strip())
    for name, result in results["cases"].items():
        print(f"\n{name} (javac exit {result['compile_exit']})")
        print(result.get("run_stdout", result["compile_stderr"]), end="")


if __name__ == "__main__":
    main()
