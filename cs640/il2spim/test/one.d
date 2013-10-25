int a,b,c
b := 2
a := b
if a > b goto L3
a := c
b := b + 1
c := 2
goto L4
L3:
a := a + 1
b := b + a
L4:
put b
