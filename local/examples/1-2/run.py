"""Compile the independent Java 8 reference examples and preserve actual output."""
import argparse
import json
from pathlib import Path
import subprocess
import tempfile


EXPECTED_OUTPUT = {
    "ReferencePuzzle": "status=1\nattempts=0\n",
    "AssignmentLab": "sameBefore=true\nfirstStatus=1\nsameAfter=false\nsecondStatus=9\n",
    "ReturnLab": "ignored=0\nreceived=9\naliasStatus=9\norderIsNull=true\n",
    "ArrayCopyLab": (
        "sameArray=false\nsameElement=true\noriginalStatus=1\n"
        "originalAfterReplace=1\ncopyStatus=9\n"
    ),
    "NullLab": (
        "statusDefault=0\nassigneeDefault=null\nunbox=NullPointerException\n"
        "field=NullPointerException\ncountElement=0\norderElement=null\n"
    ),
    "ReassignOrder": (
        "sameObject=false\nsourceAssignee=null\nassignedAssignee=7\n"
        "rejected=工单不存在\n"
    ),
}


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
    with tempfile.TemporaryDirectory(prefix="java-1-2-") as output:
        sources = sorted(root.glob("*.java")) + sorted((root / "errors").glob("*.java"))
        for source in sources:
            name = source.relative_to(root).as_posix()
            compiled = subprocess.run(
                [str(jdk / "bin/javac"), "-J-Duser.language=en", "-J-Duser.country=US",
                 "-encoding", "UTF-8", "-d", output, source.name],
                cwd=source.parent, text=True, capture_output=True)
            expected_error = source.parent.name == "errors"
            if (compiled.returncode != 0) != expected_error:
                raise SystemExit(f"Unexpected compilation result: {name}\n{compiled.stderr}")
            records["cases"][name] = {
                "compile_exit": compiled.returncode,
                "compile_stdout": compiled.stdout, "compile_stderr": compiled.stderr}
            if expected_error:
                if "variable order might not have been initialized" not in compiled.stderr:
                    raise SystemExit(f"Unexpected compiler diagnostic: {name}\n{compiled.stderr}")
                continue
            run = subprocess.run(
                [str(jdk / "bin/java"), "-Dfile.encoding=UTF-8", "-cp", output,
                 source.stem], cwd=root, text=True, capture_output=True)
            if run.returncode != 0 or run.stderr or run.stdout != EXPECTED_OUTPUT[source.stem]:
                raise SystemExit(
                    f"Unexpected result: {source.stem} (exit {run.returncode})\n"
                    f"stdout:\n{run.stdout}\nstderr:\n{run.stderr}")
            records["cases"][source.stem] = {
                "source": name, "args": [], "run_exit": run.returncode,
                "run_stdout": run.stdout, "run_stderr": run.stderr}
    (root / "results.json").write_text(
        json.dumps(records, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    print(version.strip())
    for key, result in records["cases"].items():
        if "run_exit" in result:
            print(f"\n{key} (exit {result['run_exit']})")
            print(result["run_stdout"], end="")
        elif result["compile_exit"]:
            print(f"\n{key} (javac exit {result['compile_exit']})")
            print(result["compile_stderr"], end="")


if __name__ == "__main__":
    main()
