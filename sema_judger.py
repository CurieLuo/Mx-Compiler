import os, sys, subprocess

try:
    command = "java -jar {jar_path} < {input_file} "#> {output_file}"
    path = os.path.dirname(os.path.abspath(__file__)).replace('\\', '/') + '/'
    jar_path = path + "out/artifacts/Mx_Compiler_jar/Mx-Compiler.jar"

    judge_list = open(path + "testcases/sema/judgelist.txt").readlines()

    win = 0
    lose = 0
    cnt = 0


    for judge in judge_list:
        cnt += 1
        input_file = path + "testcases/sema/" + judge.replace("\n", "")
        output_file = path + "result/" + str(cnt) + ".txt"
        print("testing case:", cnt, input_file)

        fp = open(input_file,encoding='utf-8')
        lines = fp.readlines()
        std = ""
        cmt = ""
        isSuccess = False
        for line in lines:
            if line.find("Verdict") != -1:
                std = line.replace("\n", "")
                if "Success" in std:
                    isSuccess = True
            if line.find("Comment") != -1:
                cmt = "({})".format(line.replace("\n", ""))
        print("[std]", std, cmt)
        real_command = command.format(jar_path = jar_path, input_file = input_file, output_file = output_file)
    ##    print(real_command)
        res = subprocess.call(real_command, shell = True)
        if res != 0 and isSuccess == False or res == 0 and isSuccess == True:
            print("\033[32m[Success] [test]:, point " + str(cnt))
            win += 1
        else:
            print("\033[31m[Failed] [test]:, point " + str(cnt))
            lose += 1
            #input("continue:")
        print()
    print("all:", cnt)
    print("success:", win)
except Exception as e:
    print(e)
    input()
input("quit:")
