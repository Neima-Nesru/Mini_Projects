// combo box

// Get references to necessary elements
const image = document.getElementById("myImage");
const dialog = document.getElementById("selection");


// Add click event listener to the image
image.addEventListener("click", function() {
  if (dialog.style.display == "block"){
     // hide the dialog box
    dialog.style.display = "none";
  } 
  else{
    // Show the dialog box)
    dialog.style.display = "block";
  }
});