#!/bin/bash
export PATH="/usr/local/opt/bin:$PATH"  # optional

if [ $# -eq 0 ]; then
  INPUT="test.mx"
else
  INPUT="testcases/codegen/"$1
fi
bash ./testcases/codegen/scripts/test_llvm_ir.bash './bin/mxc -ir' $INPUT bin/builtin.ll