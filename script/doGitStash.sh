projectPath=$1
cd ${projectPath}
if [ $? -eq 0 ]; then
	git stash
else
    echo FAIL
fi