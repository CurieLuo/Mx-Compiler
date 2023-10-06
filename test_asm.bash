#!/bin/bash
export PATH="/usr/local/opt/bin:$PATH"  # optional

if [ $# -eq 0 ]; then
  INPUT="test.mx"
else
  INPUT="testcases/"$1
fi
bash ./testcases/codegen/scripts/test_asm.bash './bin/mxc -S' $INPUT bin/builtin.s