const img = document.querySelector("img");

img.onclick = () => {
  const imgSrc = img.getAttribute("src");
  if (imgSrc === "images/budapest-gambit.png") {
    img.setAttribute("src", "images/dxe5.png");
  }
  else {
    img.setAttribute("src", "images/budapest-gambit.png");
  }
}

let button = document.querySelector("button");
let heading1 = document.querySelector("h1");

function checkValidName(name){
  return name !== null && name !== "";
}

function setUserName() {
  const userName = prompt("Please enter your name:");
  if (!checkValidName(userName)){

  }
  else
  {
    localStorage.setItem("name", userName);
    heading1.textContent = `Learn the Budapest Gambit, ${userName}.`;
  }
}

if (!checkValidName(localStorage.getItem("name"))){
  setUserName();
}
else {
  const storedName = localStorage.getItem("name");
  heading1.textContent = `Learn the Budapest Gambit, ${storedName}.`;
}

button.onclick = () => {
  setUserName();
}