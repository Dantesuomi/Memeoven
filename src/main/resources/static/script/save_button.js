function changeButtonImage(button) {
    var imgElement = button.querySelector("img"); // Get the img element inside the button
    imgElement.src = "/images/save_clicked.svg";
    button.onclick = null; // Disable click event after image change
}

function saveToFavorite(button, memeId) {
    var imgElement = button.querySelector("img"); // Get the img element inside the button
    imgElement.src = "/images/save_clicked.svg";
    button.onclick = null; // Disable click event after image change
    fetch(`/memes/${memeId}/save`, { method: 'POST' })
        .then(response => response.json())
        .then(data => {
            button.innerHTML = `<img src="/images/save_clicked.svg" alt="save" style="width: 16px;" class="square">`;
        })
        .catch(error => {
            console.error('Error:', error);
        });
}