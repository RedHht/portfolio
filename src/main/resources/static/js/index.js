let burguer = document.getElementById("burguer-button");
let mobileNav = document.getElementById("mobile-nav");
let navv = document.getElementById("nav-bar");

burguer.addEventListener("click", toggleNav);


function toggleNav() {
    if (mobileNav.classList.contains("hidden")) {
        mobileNav.classList.remove("hidden");
        navv.classList.add("bg-red-900");
        burguer.innerHTML = "<i class=\"fas fa-times text-white\"></i>";
    } else {
        mobileNav.classList.add("hidden");
        navv.classList.remove("bg-red-900");
        burguer.innerHTML = "<i class=\"fas fa-bars text-white\"></i>";
    }
}

function showNav() {
    mobileNav.classList.remove("hidden")

}
