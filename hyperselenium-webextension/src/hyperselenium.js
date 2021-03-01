document.body.style.border = "1rem solid red";

document.addEventListener('click', event => {
  console.log(event);
});

document.addEventListener('keyup', (event) => {
  const keyName = event.key;

  console.log(keyName);

  // As the user releases the Ctrl key, the key is no longer active,
  // so event.ctrlKey is false.
  if (keyName === 'Control') {
    console.log('Control key was released');
  }
}, false);
