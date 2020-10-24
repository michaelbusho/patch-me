
#Install libraries it they are missing
sh ./install_libraries.sh

#patch old jar using the patch file
bspatch maven-jar-plugin-2.0.jar patched.jar new_version.patch

#clean up
rm -rf new_version.patch