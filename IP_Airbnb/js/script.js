
// get started
function getStarted(){
    
    const start = document.getElementById("landlord_or_renter");
   
   if (start.style.display == "none"){
     start.style.display = "flex";

     renter = document.getElementById("renter");
     landlord = document.getElementById("landlord");

      renter.addEventListener('click',()=>{
        window.location.href="../html/homes.html";
    });

       landlord.addEventListener('click',()=>{
        window.location.href="../html/login.html";
    });
   }
   else{
    start.style.display = "none";
   }

}
// go to login or signup
function gotoLogin(){
 
        window.location.href="../html/login.html";

}
function gotoSignUp(){
 
        window.location.href="../html/signup.html";

}
// login

function logIn(){
    var loginInput1 = document.getElementById('login-email');
    var loginInput2 = document.getElementById('login-password');

// if both input fields empty
   if (loginInput1.value.length == 0 || loginInput2.value.length == 0)
   {
    if (loginInput1.value.length == 0)
    {

         error1.textContent = "* Email is required !";
  
    }
    if (loginInput2.value.length == 0)
    {

           error2.textContent = "* Password is required !";
    }

   }

   // input fields not empty but not completely filled
   else 
   {
            if (loginInput1.value.indexOf("@")==-1)
            {

                error1.textContent = "Invalid Email !";


            }
  
            else if (loginInput2.value.length > 0 && loginInput2.value.length < 8)
            {
                error2.textContent = "Password must be morethan 8 character !";
            }

             else
             {
                error1.textContent = "";
                error2.textContent = "";

            // redirect to home page
            window.location.href="../html/homes.html";

                
             }
   }
 
}

// signup

const firstname = document.getElementById("input1");

const button = document.getElementById("btn");

const array = ['Last Name',
                'Email',
                 'Birthday',
                 'Country',
                 'City',
                 'Password',
                 'Confirm Password',
                 ];
let i = 0;
button.addEventListener('click',()=>{
error = document.getElementById("error");
    if (firstname.value.length == 0) {
         error.textContent = '';
        error.textContent = '* required';
    }
    else if (firstname.value.length < 5) {

        error.textContent = '';
        error.textContent = 'too Short !';
    }
    else if (firstname.placeholder == 'Email' && firstname.value.indexOf('@') == -1){
       error.textContent = '';
        error.textContent = 'Invalid Email !';

    }
   
    else if (firstname.placeholder == 'Password' && firstname.value.length < 8){
        
        error.textContent = '';
        error.textContent = 'Password should be strong !';
    }

    else{
        error.textContent = '';
        firstname.value = '';
        firstname.placeholder = array[i];

         i++;
         if (i == 3){
            firstname.type = 'date';
         }
         else if (i == 6 || i == 7){
            firstname.type = 'password';
         }
         else{
            firstname.type = 'text';
         }

    if (i == array.length - 1){
        // firstname.style.display = 'none';
      
        button.value = "Sign Up";
        button.addEventListener('click',()=>{
        window.location.href="../html/homes.html";
    });
    } 
    }

      
});


// Function to capture form inputs and store them in localStorage
function storeFormInputs(event) {
  event.preventDefault();

  // Get form values
  const name = document.getElementById('name').value;
  const email = document.getElementById('email').value;
  const age = document.getElementById('age').value;

  // Store form values in localStorage
  localStorage.setItem('name', name);
  localStorage.setItem('email', email);
  localStorage.setItem('age', age);

  // Clear form inputs
  event.target.reset();

  // Call function to populate profile page
  displayProfile();
}

// Function to retrieve stored information and populate the profile page
function displayProfile() {
  // Get stored form values from localStorage
  const storedName = localStorage.getItem('name');
  const storedEmail = localStorage.getItem('email');
  const storedAge = localStorage.getItem('age');

  // Update profile page with stored values
  document.getElementById('profileName').textContent = storedName;
  document.getElementById('profileEmail').textContent = storedEmail;
  document.getElementById('profileAge').textContent = storedAge;
}

// Add event listener to the form submit event
document.getElementById('signupForm').addEventListener('submit', storeFormInputs);

// Call function to populate profile page on page load
displayProfile();
