
#Install libraries it they are missing
sh ./install_libraries.sh

#Go to resources
cd ../src/main/resources

if ls *.patch &> /dev/null; then
    echo "Patch is already generated - Remove it to force generating it again"
else
  PRIOR_V="0.0.3"
  POST_V="0.0.4"

  function usage()
  {
      echo "To run the patch creation script you need to specify prior and after version"
      echo ""
      echo "./create_patch.sh"
      echo "\t-h --help"
      echo "\t--prior=$PRIOR_V"
      echo "\t--post=$POST_V"
      echo ""
  }

  while [ "$1" != "" ]; do
      PARAM=`echo $1 | awk -F= '{print $1}'`
      VALUE=`echo $1 | awk -F= '{print $2}'`
      case $PARAM in
          -h | --help)
              usage
              exit
              ;;
          --prior)
              PRIOR_V=$VALUE
              ;;
          --post)
              POST_V=$VALUE
              ;;
          *)
              echo "ERROR: unknown parameter \"$PARAM\""
              usage
              exit 1
              ;;
      esac
      shift
  done

  #------------- RPM ---------------
  #Download older and newer version of rpm
  wget -O prior_rpm.jar -nc https://github.com/michaelbusho/patch-me/releases/download/"$PRIOR_V"/rpm-sensor-"$PRIOR_V".jar
  wget -O post_rpm.jar -nc https://github.com/michaelbusho/patch-me/releases/download/"$POST_V"/rpm-sensor-"$POST_V".jar

  #create rpm patch
  bsdiff prior_rpm.jar post_rpm.jar rpm-sensor-0.0.3.patch

  #clean up rpm jars
  rm -rf prior_rpm.jar post_rpm.jar

  #------------- TEMP ---------------
  #Download older and newer version of temp
  wget -O prior_temp.jar -nc https://github.com/michaelbusho/patch-me/releases/download/"$PRIOR_V"/temp-sensor-"$PRIOR_V".jar
  wget -O post_temp.jar -nc https://github.com/michaelbusho/patch-me/releases/download/"$POST_V"/temp-sensor-"$POST_V".jar

  #create temp patch
  bsdiff prior_temp.jar post_temp.jar temp-sensor-0.0.3.patch

  #clean up rpm jars
  rm -rf prior_temp.jar post_temp.jar
fi


