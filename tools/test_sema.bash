#!/bin/bash
export PATH="/usr/local/opt/bin:$PATH"  # optional

if [ $# -eq 0 ]; then
  INPUT="test.mx"
else
  INPUT="testcases/sema/"$1
fi
bash ./testcases/sema/scripts/test.bash './bin/mxc -fsyntax-only' $INPUT