"""Compile the independent Java 8 examples and preserve their actual output."""
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
    version = subprocess.run([str(jdk / "bin/java"), "-version"],
                             capture_output=True, text=True, check=True).stderr
    if 'version "1.8.' not in version:
        raise SystemExit("These examples require JDK 8.")
    root = Path(__file__).resolve().parent
    records = {"jdk": version, "cases": {}}
    with tempfile.TemporaryDirectory(prefix="java-1-1-") as output:
        for source in sorted(root.glob("*.java")) + sorted((root / "errors").glob("*.java")):
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
            if not expected_error:
                inputs = [(source.stem, [], 0)] if source.stem != "PriceQuote" else [
                    ("PriceQuote", ["19.90", "3", "0.85"], 0),
                    ("PriceQuote-small", ["0.05", "3", "0.10"], 0),
                    ("PriceQuote-extra-zero", ["19.900", "3", "0.85"], 0),
                    ("PriceQuote-fraction", ["19.901", "3", "0.85"], 2),
                    ("PriceQuote-rate", ["19.90", "3", "1.01"], 2),
                    ("PriceQuote-quantity", ["19.90", "0", "0.85"], 2),
                    ("PriceQuote-nan", ["NaN", "3", "0.85"], 2),
                    ("PriceQuote-large-count", ["19.90", "2147483648", "0.85"], 2),
                    ("PriceQuote-usage", [], 2)]
                for key, arguments, expected_exit in inputs:
                    run = subprocess.run(
                        [str(jdk / "bin/java"), "-Dfile.encoding=UTF-8", "-cp", output,
                         source.stem, *arguments], cwd=root, text=True, capture_output=True)
                    if run.returncode != expected_exit:
                        raise SystemExit(f"Unexpected exit: {key}\n{run.stderr}")
                    records["cases"][key] = {
                        "source": name, "args": arguments, "run_exit": run.returncode,
                        "run_stdout": run.stdout, "run_stderr": run.stderr}
    # Verify the surprises and the complete program's behavior before publishing the record.
    cases = records["cases"]
    assert cases["PriceOverflow"]["run_stdout"] == "-1794967296\n"
    assert "castBefore=2500000000\n" in cases["NumberLab"]["run_stdout"]
    assert "mixedEquality=true\nintegerEquality=false\n" in cases["NumberLab"]["run_stdout"]
    assert "valueOfSum=0.30000000000000004\n" in cases["DecimalLab"]["run_stdout"]
    assert "roundEach=0.03\nroundTotal=0.02\n" in cases["RoundingLab"]["run_stdout"]
    assert cases["PriceQuote"]["run_stdout"] == "原价=59.70\n应付=50.75\n"
    assert cases["PriceQuote-small"]["run_stdout"] == "原价=0.15\n应付=0.02\n"
    assert cases["PriceQuote-extra-zero"]["run_stdout"] == cases["PriceQuote"]["run_stdout"]
    assert cases["PriceQuote-fraction"]["run_stderr"] == "输入错误：单价必须能精确到分\n"
    for key in ["PriceQuote-rate", "PriceQuote-quantity"]:
        assert "单价须非负，数量须为正整数" in cases[key]["run_stderr"]
    for key in ["PriceQuote-nan", "PriceQuote-large-count"]:
        assert "数量须为 int 范围内的整数" in cases[key]["run_stderr"]
    (root / "results.json").write_text(
        json.dumps(records, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    print(version.strip())
    for key, result in cases.items():
        if "run_exit" in result:
            print(f"\n{key} (exit {result['run_exit']}, args={result['args']})")
            print(result["run_stdout"] + result["run_stderr"], end="")
        elif result["compile_exit"]:
            print(f"\n{key} (javac exit {result['compile_exit']})")
            print(result["compile_stderr"], end="")


if __name__ == "__main__":
    main()
