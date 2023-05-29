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
    const counterElement = document.getElementById('likeCount');
    let currentValue = parseInt(counterElement.textContent);
    let updatedValue = currentValue + 1;
    counterElement.textContent = updatedValue;
}