projectPath=$1
cd $1
if [ $? -eq 0 ]; then
	./gradlew clean assemble
else
    echo FAIL
fi