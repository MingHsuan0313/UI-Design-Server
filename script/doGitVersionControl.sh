mode=$1
projectPath=$2
service_name=$3
echo $2
cd ${projectPath} || git add . && git commit -m "$2: ${service_name}"