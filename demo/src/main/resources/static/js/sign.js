document.addEventListener('DOMContentLoaded', function () {
    // Universal form section animation (applies to both login/signup sections)
    const formSection = document.querySelector('section');
    if (formSection) { // Check if section exists
        formSection.style.opacity = 0;
        setTimeout(() => {
            formSection.style.transition = 'opacity 1s ease-in-out';
            formSection.style.opacity = 1;
        }, 500);
    }

    // Attempt to get the button. Use a more specific selector to avoid issues if other buttons exist.
    const submitButton = document.querySelector('form button');

    // Only add the click listener if a submit button is found
    if (submitButton) {
        submitButton.addEventListener('click', function (event) {
            // --- Logic for LOGIN page (checks for username and password) ---
            const usernameInput = document.getElementById('username');
            const loginPasswordInput = document.getElementById('password'); // This is the password for login

            let isLoginPage = false;
            // Differentiate login from signup by checking form action
            // and the presence of specific login fields (usernameInput for type="name" or "text", passwordInput for login)
            if (usernameInput && loginPasswordInput && submitButton.closest('form').action.includes('/req/login')) {
                isLoginPage = true;
            }

            if (isLoginPage) {
                const isUsernameValid = usernameInput.checkValidity();
                const isLoginPasswordValid = loginPasswordInput.checkValidity();

                if (!isUsernameValid || !isLoginPasswordValid) {
                    event.preventDefault(); // Prevent form submission if validation fails
                    if (formSection) {
                        formSection.classList.add('shake');
                        setTimeout(() => {
                            formSection.classList.remove('shake');
                        }, 1000);
                    }
                }
                return; // Stop here if it's the login page
            }

            // --- Logic for SIGNUP page (checks for email, password, confirm-password) ---
            // These selectors should match your signup.html structure
            const usernameSignupInput = document.getElementById('username'); // Assuming username is also present on signup form
            const emailInput = document.getElementById('email'); // Changed to getElementById for consistency and directness
            const signupPasswordInput = document.getElementById('password');
            const confirmPasswordInput = document.getElementById('passwordcon'); // Changed to getElementById for consistency and directness


            // IMPORTANT: Check if elements exist before calling checkValidity
            let isSignupFormValid = true;

            // Validate username (if present on signup form)
            if (usernameSignupInput) {
                isSignupFormValid = isSignupFormValid && usernameSignupInput.checkValidity();
            } else {
                // If username input is expected on signup page but not found, something is wrong
                isSignupFormValid = false;
            }

            // Validate email
            if (emailInput) {
                isSignupFormValid = isSignupFormValid && emailInput.checkValidity();
            } else {
                 isSignupFormValid = false;
            }

            // Validate password (for signup)
            if (signupPasswordInput) {
                isSignupFormValid = isSignupFormValid && signupPasswordInput.checkValidity();
            } else {
                 isSignupFormValid = false;
            }

            // Validate confirm password
            if (confirmPasswordInput) {
                isSignupFormValid = isSignupFormValid && confirmPasswordInput.checkValidity();
            } else {
                 isSignupFormValid = false;
            }

            // Add custom password matching logic for signup
            if (signupPasswordInput && confirmPasswordInput && signupPasswordInput.value !== confirmPasswordInput.value) {
                isSignupFormValid = false;
                confirmPasswordInput.setCustomValidity("Le password non corrispondono."); // Set a custom validation message
            } else if (confirmPasswordInput) {
                confirmPasswordInput.setCustomValidity(""); // Clear custom validation if they match
            }


            if (!isSignupFormValid) {
                event.preventDefault(); // Prevent form submission if client-side validation fails
                if (formSection) {
                    formSection.classList.add('shake');
                    setTimeout(() => {
                        formSection.classList.remove('shake');
                    }, 1000);
                }
                // Stop here if client-side validation failed
                return;
            }

            // --- Logica FETCH per la Registrazione (AJAX) ---
            // Esegue solo se isSignupFormValid è true
            event.preventDefault(); // Impedisci la sottomissione standard del form per fare la richiesta AJAX

            const username = usernameSignupInput.value;
            const password = signupPasswordInput.value;
            const email = emailInput.value;

            const data = {
                username,
                email,
                password
            };

            const jsonData = JSON.stringify(data);

            // L'URL /req/signup POST è gestito dal tuo RegistrationController
            const backendUrl = "https://p01--backend--ls828smnbk6x.code.run";

fetch(`${backendUrl}/req/signup`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: jsonData
            })
            .then(response => {
                if (response.ok) { // Status code 2xx (es. 200 OK, 201 Created)
                    alert('Registrazione avvenuta con successo! Effettua il login.');
                    window.location.href = '/req/login'; // Reindirizza al login dopo il successo
                } else {
                    // Leggi il messaggio di errore dal backend
                    return response.text().then(errorMessage => {
                        throw new Error(errorMessage || 'Errore sconosciuto durante la registrazione');
                    });
                }
            })
            .catch(error => {
                console.error('Errore durante la registrazione:', error);
                alert('Errore durante la registrazione: ' + error.message);
                if (formSection) {
                    formSection.classList.add('shake'); // Scuoti il form anche per errori backend
                    setTimeout(() => {
                        formSection.classList.remove('shake');
                    }, 1000);
                }
            });
            // --- FINE Logica FETCH ---

        }); // End of submitButton click listener
    } else {
        console.warn("No form submit button found to attach validation logic to.");
    }
});

/*document.addEventListener('DOMContentLoaded', function () {
    const signupForm = document.querySelector('section');
    signupForm.style.opacity = 0;
  
    setTimeout(() => {
      signupForm.style.transition = 'opacity 1s ease-in-out';
      signupForm.style.opacity = 1;
    }, 500);
  
    const signupButton = document.querySelector('button');
    signupButton.addEventListener('click', function () {
      const emailInput = document.querySelector('input[type="email"]');
      const passwordInput = document.querySelector('input[type="password"]');
      const confirmPasswordInput = document.querySelector('input[type="password"][name="confirm-password"]');
  
      // Check for a valid email and password (you can add your validation logic here)
      const isValid = emailInput.checkValidity() && passwordInput.checkValidity() && confirmPasswordInput.checkValidity();
  
      if (!isValid) {
        signupForm.classList.add('shake');
  
        setTimeout(() => {
          signupForm.classList.remove('shake');
        }, 1000);
      }
    });
  });
  */