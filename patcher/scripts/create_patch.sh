
#Install libraries it they are missing
sh ./install_libraries.sh

#Go to resources
cd ../src/main/resources

if ls *.patch &> /dev/null; then
    echo "Patch is already generated - Remove it to force generating it again"
else
    #Download older and newer version
    wget -nc https://github.com/bbchristians/SATDBailiff/releases/download/1.1/SATDBailiff-1.1.jar
    wget -nc https://github.com/bbchristians/SATDBailiff/releases/download/0.2-alpha/satd-analyzer-0.2-alpha.jar

    #create patch
    bsdiff satd-analyzer-0.2-alpha.jar SATDBailiff-1.1.jar new_version.patch

    #clean up
    rm -rf satd-analyzer-0.2-alpha.jar SATDBailiff-1.1.jar
fi


