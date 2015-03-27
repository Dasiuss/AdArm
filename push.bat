IF %1.==. GOTO No1

git add .
git commit -m %1
git push

GOTO End1

:No1
  ECHO podaj opis commitu
:End1