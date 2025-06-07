/* eslint-disable no-constant-condition */
/* eslint-disable no-undef */

//Resize
let mainMessages = document.getElementById("mainMessages");

addEventListener("resize", (event) => {
    resizeMessages();
});

function resizeMessages() {
    let windowHeight = window.innerHeight;
    let headerNavbarHeight = document.getElementById("headerNavbar").scrollHeight;
    let mainInputContainerHeight = document.getElementById("mainInputContainer").scrollHeight;
    
    mainMessages.style.height = `calc(${windowHeight}px - ${headerNavbarHeight}px - ${mainInputContainerHeight}px + 1px)`;
}
resizeMessages();

//Process messages

function scrollToBottom() {
    setTimeout(() => {
        mainMessages.scrollTop = mainMessages.scrollHeight;
    }, 150);
}
scrollToBottom();

//send user message
document.addEventListener('keydown', function(event) {
    if (event.code == 'Enter') {
        sendMessage();
    }
});

function sendMessage() {
    let message = document.getElementById("mainInputMessage").value.trim();
    let userUUID = getUserUUID();

    if (message != "") {
        $.ajax({
            type: 'POST',
            url: '/send',
            contentType: 'application/json',
            data: JSON.stringify({
                message: message,
                userUUID: userUUID,
            }),
            success: function() {
                document.getElementById("mainInputMessage").value = "";
                getMessages();
                scrollToBottom();
            },
            error: function() {
            }
        });
    }
}

//get all messages and uuids
function getMessages() {
    $.ajax({
        type: 'POST',
        url: '/getMessages',
        contentType: 'application/json',
        success: function(messagesList) {
            getUserUUIDs(messagesList);
        },
        error: function() {
        }
    });
}

function getUserUUIDs(messagesList) {
    $.ajax({
        type: 'POST',
        url: '/getUUIDs',
        contentType: 'application/json',
        success: function(uuidsList) {
            processMessages(uuidsList, messagesList);
        },
        error: function() {
        }
    });
}

function processMessages(uuidsList, messagesList) {
    mainMessages.innerHTML = "";

    if (messagesList != "") {

        let userUUID = getUserUUID();

        for (let i = 0; i < messagesList.length; i++) {
            let newMessage = document.createElement("p");
    
            if (uuidsList[i] == userUUID) {
                newMessage.classList.add("myMessage");
            } else {
                newMessage.classList.add("otherMessage");
            }
    
            newMessage.innerHTML = messagesList[i];

            mainMessages.appendChild(newMessage);
        }
    }

}

//UUID
function getUserUUID() {
    let userUUID = localStorage.getItem("userUUID");

    if (userUUID == null) {
        userUUID = crypto.randomUUID();
        localStorage.setItem("userUUID", userUUID);
    }

    return userUUID;
}
getUserUUID();

//timer for messages
function getMessagesEveryTime() {
    getMessages();
    setTimeout(getMessagesEveryTime, 3000);
}
getMessagesEveryTime();