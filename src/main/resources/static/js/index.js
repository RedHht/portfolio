let burguer = document.getElementById("burguer-button");
let mobileNav = document.getElementById("mobile-nav");
let navv = document.getElementById("nav-bar");
let navColor = "bg-red-400"

burguer.addEventListener("click", toggleNav);


function toggleNav() {
    if (mobileNav.classList.contains("hidden")) {
        mobileNav.classList.remove("hidden");
        navv.style.backgroundColor = "red"
        burguer.innerHTML = "<i class=\"fas fa-times text-white\"></i>";
    } else {
        mobileNav.classList.add("hidden");
        navv.style.backgroundColor = "transparent"
        burguer.innerHTML = "<i class=\"fas fa-bars text-white\"></i>";
    }
}

function showNav() {
    mobileNav.classList.remove("hidden")

}
