f = open("eco.txt", "r")
o = open("output.txt", "w")

save = False
s = ""
i = 0
totalopenings = 0
arr = []

def addSpaces(line):
    stripped = line.rstrip()
    newline = ""
    for i, c in enumerate(stripped):
        if c == '.' and i < len(stripped)-1 and stripped[i+1] != ' ':
            # newline.append(". ")
            newline += ". "
        else:
            # newline.append(c)
            newline += c
    return newline

def writeIfGood(arr, s, i):
    if (len(s) > 20):
        arr.append("opening eco" + str(i) + "\n")
        arr.append(s + "\n")


for line in f:
    if (line[0] == '1' and line[1] == '.'):
        totalopenings += 1
        save = True
        # s += line.rstrip()
        s += addSpaces(line)
    elif (len(line) >= 3 and line[0] != " " and line[0] != "[" and save == True):
        # s += " " + line.rstrip()
        s += " " + addSpaces(line)
    elif (len(line) == 1 and save == True):
        i += 1
        # o.writelines("opening " + str(i))
        # o.writelines(s)
        writeIfGood(arr, s, i)
        s = ""
        save = False

if (save == True):
    i += 1
    # o.write("opening " + str(i) + "\n")
    # o.write(s + "\n")
    writeIfGood(arr, s, i)

o.writelines(arr)
print(f"total lines {len(arr)}")
print(f"total openings {totalopenings}")

f.close()
o.close()



            
    

