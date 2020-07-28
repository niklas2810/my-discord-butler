const rel = getResourcePathWithOrigin();
console.log("Using path: ", rel);
replaceImgTags();


function replaceImgTags() {
    var all = document.getElementsByTagName("img");
    for(let item of all) {
        replaceImgTag(item);
    }
}

function replaceImgTag(img) {
    if (img.src === rel+"redbar.gif") {
        img.src = rel + "empty.svg";
        img.style = "background-image: url("+rel+"red.svg);";
    } else if (img.src === rel + "greenbar.gif") {
        img.src = rel+"empty.svg";
        img.style = "background-image: url(" + rel + "green.svg);";
    } else {
        console.log("No match");
    }
}