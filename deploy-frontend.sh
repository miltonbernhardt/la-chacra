cd ui || exit
npm run build
cd ..
rm -r -f src/main/resources/front
cp -r ui/build/ src/main/resources/front
