'use strict';
let table;
let array = [];
const modal = document.querySelector('.mod');
const overlay = document.querySelector('.overlay');
let tr;
const btnCloseModal = document.querySelector('.close-modal');
let alertModal = document.querySelector('.alertModal');
let deleteBtn = document.querySelector('#delete');
let isValidFile = document.getElementById('file');


function getRequest() {
    table = $('.table#bookTable').DataTable({
        ajax: {
            url: 'http://localhost:8081/bookApp/', method: "GET", dataSrc: ''
        }, columns: [{data: 'id'}, {data: 'bookName'}, {data: 'bookAuthor'}, {data: 'bookPages'}, {
            "data": "pdfName", fnCreatedCell: function (nTd, sData, oData, iRow, iCol) {
                $(nTd).html(" <a class='material-symbols-outlined' target='_blank' href='http://localhost:8081/bookApp/download/" + oData.pdfName + "'>" + 'picture_as_pdf' + "</a>");
            }
        },]
    },);
}

async function postRequest() {
    let bookDto = {
        bookName: $("#bookName").val(), bookAuthor: $("#bookAuthor").val(), bookPages: $("#bookPages").val(),
    };
    let formData = new FormData();

    formData.append('bookName', bookDto.bookName);
    formData.append('bookAuthor', bookDto.bookAuthor);
    formData.append('bookPages', bookDto.bookPages);
    formData.append('pdf', $('#file')[0].files[0]);

    let response = await fetch('http://localhost:8081/bookApp', {
        method: "POST", body: formData
    });
    if (response.status === 201) {
        table.destroy();
        getRequest();
        clearForm()
        alert("Book Added");
    } else {
        alert("Ups")
    }
}

async function putRequest() {
    let bookDto = {
        id: array.at(0),
        bookName: $("#modalBookName").val(),
        bookAuthor: $("#ModalBookAuthor").val(),
        bookPages: $("#ModalBookPages").val(),
    };
    let formData = new FormData();

    formData.append('id', bookDto.id);
    formData.append('bookName', bookDto.bookName);
    formData.append('bookAuthor', bookDto.bookAuthor);
    formData.append('bookPages', bookDto.bookPages);
    formData.append('pdf', $('#modalFile')[0].files[0]);

    let response = await fetch('http://localhost:8081/bookApp', {
        method: "PUT", body: formData
    });
    if (response.status === 200) {
        table.destroy();
        getRequest();
        closeModal();
        alert("Book updated");
    } else {
        alert("UPS")
    }
}


function deleteRequest() {
    $.ajax({
        type: "DELETE",
        url: `http://localhost:8081/bookApp/${array.at(0)}`,
        contentType: 'application/json;charset=UTF-8',
    }).done(function () {
        table.destroy();
        getRequest();
        closeModal();
        alert("Book Deleted");
    })
}

getRequest();

table.on('draw.dt', function () {
    beforeOpenModal()
})

function beforeOpenModal() {
    tr = document.querySelectorAll('tbody tr');
    for (let trToTd of tr) {
        let td = trToTd.querySelectorAll('td');
        for (let tdElement of td) {
            if (tdElement.childElementCount === 0 && !tdElement.classList.contains("dataTables_empty")) tdElement.addEventListener('click', function () {
                array = [];
                for (let tdElement of td) {
                    array.push(tdElement.innerHTML);
                }
                openModal(...array);
            });
        }

    }
}


const openModal = function (id, bookName, bookAuthor, bookPages) {
    modal.classList.remove('hidden');
    overlay.classList.remove('hidden');
    modal.querySelector('#modalBookName').value = bookName;
    modal.querySelector('#ModalBookAuthor').value = bookAuthor;
    modal.querySelector('#ModalBookPages').value = bookPages;
};

function closeModal() {
    $("#modalFile").val("")
    modal.classList.add('hidden');
    overlay.classList.add('hidden')

}

function hiddenAlert() {
    alertModal.classList.add('hidden');
}

function clearForm() {
    $("#bookName").val("")
    $("#bookAuthor").val("")
    $("#bookPages").val("")
    $("#file").val("")
}

isValidFile.onchange = function (e) {
    let ext = this.value.match(/\.([^\.]+)$/)[1];
    switch (ext) {
        case 'pdf':
            break;
        default:
            alert('Not allowed');
            this.value = '';
    }
};

function alert(alertString) {
    alertModal.classList.remove('hidden');
    alertModal.textContent = alertString;
    setTimeout(hiddenAlert, 1000);
}

document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape' && !modal.classList.contains('hidden')) {
        closeModal();
    }
})
document.addEventListener('keydown', function (e) {
    if (e.key === 'Enter' && modal.classList.contains('hidden')) {
        postRequest();
    }
})
document.addEventListener('keydown', function (e) {
    if (e.key === 'Enter' && !modal.classList.contains('hidden')) {
        putRequest();
    }
})
document.addEventListener('keydown', function (e) {
    if (e.key === 'Delete' && !modal.classList.contains('hidden')) {
        deleteRequest();
    }
})
document.querySelector('#registerButton').addEventListener('click', postRequest)
document.querySelector('#modalUpdateBtn').addEventListener('click', putRequest);
deleteBtn.addEventListener('click', deleteRequest);
btnCloseModal.addEventListener('click', closeModal);
overlay.addEventListener('click', closeModal);