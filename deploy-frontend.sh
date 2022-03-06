cd ui || exit
npm run build
cd ..
rm -r -f target/classes/static
cp -r ui/build/ target/classes/static/
