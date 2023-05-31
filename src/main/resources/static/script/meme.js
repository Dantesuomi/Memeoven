function likeMeme(memeId) {
    fetch(`/like/${memeId}/userLiked`)
        .then(response => response.text())
        .then(hasUserLiked => {
                if(hasUserLiked === 'false'){
                    increaseMemeLikeCount(memeId);
                }
            }
        )
}

function increaseMemeLikeCount(memeId) {
    const options = {
        method: 'POST'
    };
    fetch(`/like/${memeId}`, options);
    const counterElement = document.getElementById('meme' + memeId);
    let currentValue = parseInt(counterElement.textContent);
    let updatedValue = currentValue + 1;
    counterElement.textContent = updatedValue;
}

function saveToFavourite(memeId) {
    fetch(`/meme/${memeId}/isFavouriteMeme`)
        .then(response => response.text())
        .then(hasUserFavourite => {
                if(hasUserFavourite === 'false'){
                    changeFavouriteImage(memeId);
                }
            }
        );
}

function changeFavouriteImage(memeId) {
    const options = {
        method: 'POST'
    };
    fetch(`/meme/${memeId}/favourite`, options);
    const imageElement = document.getElementById('favouriteMeme' + memeId);
    imageElement.src = "/images/save_clicked.svg";
}