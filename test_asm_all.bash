#!/bin/bash
export PATH="/usr/local/opt/bin:$PATH"  # optional
bash ./testcases/codegen/scripts/test_asm_all.bash './bin/mxc -S' testcases/codegen bin/builtin.s 