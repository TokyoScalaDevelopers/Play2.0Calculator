countdown() {
    for I in {5..1}; do echo -n "$I... "; sleep 1; done
    echo
}
countdownq() {
    sleep 5
    echo
}
clear
echo ================================================================================
echo 1: Clean out your repo
echo Using \`git clean -df\` will remove everything ignored in your .gitignore files.
echo This will simulate the clean environment on heroku\'s servers.
echo ================================================================================
countdown
set -x
git clean -df
git status
set +x
echo \`git status\` was just to prove to ourselves that our environment is, in fact,
echo clean.
countdownq

clear
echo ================================================================================
echo 2: Build your app\'s standalone server
echo Heroku\'s documentation states:
echo "    At deploy time, Heroku runs sbt clean compile stage to build your Scala app."
echo Since our goal right now is to replicate Heroku\'s environment, let\'s do that.
echo ================================================================================
countdown
set -x
sbt clean compile stage
set +x
countdownq

clear
echo ================================================================================
echo 3: Test with Heroku\'s process manager, \`foreman\`
echo \`foreman\` uses a special file called \"Procfile\" to determine which processes
echo it needs to run. Currently our project does not have one, so let\'s create it.
echo After that, we can try to run our application with foreman.
echo We\'ll let foreman run for a couple seconds, then tell it to stop.
echo ================================================================================
countdown
set -x
echo 'web: target/start -Dhttp.port=$PORT' > Procfile
foreman start &
sleep 5 && kill -SIGTERM $!
set +x
countdownq

clear
echo ================================================================================
echo 4: Push to Heroku
echo Now that \`foreman\` seems happy, we can safely push to Heroku.
echo Make sure to commit your \"Procfile\", then...
echo \`heroku create \[appname\]\` to create your Heroku application
echo \`git push heroku master\` to deploy to Heroku.
echo ================================================================================
countdown
set -x
git add Procfile
git commit -m 'Adding Heroku Procfile'
heroku create tsdcalc
git push heroku master
set +x
countdownq

clear
echo ================================================================================
echo 5: Test your application on Heroku
echo \`heroku open\` will tell your default browser to navigate to your freshly
echo deployed app\!
echo ================================================================================
countdownq
