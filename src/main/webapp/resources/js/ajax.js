function checkIfTableHasAnyRow(taskTable, taskTableParentDiv) {
    var taskTableBody = taskTable.children("tbody");
    if (taskTableBody.children("tr").length < 1){
        removeTable(taskTable);
        createInformationDiv(taskTableParentDiv);
    }
}

function removeButton(buttonToRemove){
    buttonToRemove.remove();
}

function removeTable(taskTable){
    taskTable.remove();
}

function createInformationDiv(taskTableParentDiv){
    var div = $("<div class='text-center'></div>");
    $(div).append($("<h2>You do not have any task...</h2>"));
    div.appendTo(taskTableParentDiv);
}

function createSuccessInformationButton(buttonParentTd, action) {
    var text = action == "deleted" ? "Deleted successfully." : "Status updated";
    buttonParentTd.prepend($('<button/>', {
        class: 'btn btn-outline-success',
        text: text,
        disabled: 'disabled'
    }));
}

function disableDoneAndMoreDetailsButtons(taskId) {
    var doneButtonId = "#markAsDoneTask" + taskId;
    var moreDetailsButtonId = "#moreDetailsTask" + taskId;
    $(moreDetailsButtonId).attr("disabled", "disabled");
    $(doneButtonId).attr("disabled", "disabled");
}

function createSuccessInformationDiv(taskTableParentDiv){
    var containerDiv= $("<div>");
    var informationDiv = $("<div class='alert alert-success'></div>");
    var text = $("<h3>The task was deleted successfully</h3>");
    var button = $("<button class='btn btn-outline-primary'>Go back</button>");
    button.click(function () {
        history.back();
    });
    informationDiv.append(text);
    containerDiv.append(informationDiv);
    containerDiv.append(button);
    taskTableParentDiv.append(containerDiv);
}

function setDeletedTrRemoveTimeout(deleteButtonParentTr, taskTable, taskTableParentDiv) {
    setTimeout(function () {
        deleteButtonParentTr.remove();
        checkIfTableHasAnyRow(taskTable, taskTableParentDiv);
    }, 1500);
}

function showErrorMessage() {
    $("#content").empty();
    var errorContentDiv = $("<div class='card text-center'></div>");
    var errorHeaderDiv = $("<div class='card-header bg-danger text-light'></div>");
    var errorBodyDiv = $("<div class='card-body'></div>");
    var errorFooterDiv = $("<div class='card-footer'></div>");
    errorHeaderDiv.append($("<h1>Sorry...</h1>"));
    errorBodyDiv.append($("<p class='card-text'></p>").append($("<h3>Something went wrong</h3>")));
    errorFooterDiv.append($("<a href='/'></a>").append($("<button class='btn btn-outline-danger'>Home page</button>")));
    errorContentDiv.append(errorHeaderDiv)
        .append(errorBodyDiv)
        .append(errorFooterDiv);
    $("#content").append(errorContentDiv);
}

function deleteTask(){

    var token = $("meta[name = '_csrf']").attr("content");
    var header = $("meta[name = '_csrf_header']").attr("content");

    $.ajaxSetup({
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        }
    });

    var deleteButton = $(this);
    var taskId = deleteButton.attr("id").replace("deleteTask", "");
    var data = {"taskId": taskId};

    $.ajax({
        type: "DELETE",
        url: "/deleteTask",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json",
        success: function () {

            var taskTable = deleteButton.closest("table");
            var taskTableParentDiv = taskTable.closest("div");
            //todays tasks and other tasks view
            if (taskTable.attr("id") != "taskDetails"){
                var deleteButtonParentTd = deleteButton.closest("td");
                var deleteButtonParentTr = deleteButtonParentTd.closest("tr");
                removeButton(deleteButton);
                createSuccessInformationButton(deleteButtonParentTd, "deleted");
                disableDoneAndMoreDetailsButtons(taskId);
                setDeletedTrRemoveTimeout(deleteButtonParentTr, taskTable, taskTableParentDiv);
                //task details view
            } else{
                removeTable(taskTable);
                createSuccessInformationDiv(taskTableParentDiv);
            }

        },
        error: function () {
            showErrorMessage();
        }
    })

}

function markTaskAsDone(){

    var token = $("meta[name = '_csrf']").attr("content");
    var header = $("meta[name = '_csrf_header']").attr("content");

    $.ajaxSetup({
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        }
    });

    var doneButton = $(this);
    var taskId = doneButton.attr("id").replace("markAsDoneTask", "");
    var data = {"taskId": taskId};

    $.ajax({
        type: "PUT",
        url: "/markTaskAsDone",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json",
        success: function () {

            var taskImgId = "#imgTask" + taskId;
            var doneButtonParentTd = doneButton.closest("td");
            removeButton(doneButton);
            $(taskImgId).attr("src", "/resources/img/checked.png");
            createSuccessInformationButton(doneButtonParentTd, "markedAsDone");

        },
        error: function () {
            showErrorMessage();
        }
    })

}

$(document).ready(function () {
    $(".deleteButton").click(deleteTask);
    $(".doneButton").click(markTaskAsDone);
})