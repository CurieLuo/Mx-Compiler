#!/bin/bash
./bin/mxc -ir <test.mx >test.ll
clang-15 test.ll bin/builtin.ll -m32 -o exe
./exe
rm exe