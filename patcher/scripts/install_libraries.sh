#Download brew if it does not exist
if ! type "brew" > /dev/null; then
    ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)" < /dev/null 2> /dev/null
fi

#Download bsdiff if it does not exist
if ! type "bsdiff" > /dev/null; then
    brew install bsdiff
fi