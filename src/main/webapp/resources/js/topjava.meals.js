const ctx = {
    ajaxUrl: "profile/meals/",
    datatableApi: null
};

$(function () {
    ctx.datatableApi = $('#datatable').DataTable({
        ajax: {
            url: ctx.ajaxUrl,
            dataSrc: ''
        },
        columns: [
            {data: 'dateTime'},
            {data: 'description'},
            {data: 'calories'},
            {
                data: null,
                orderable: false,
                defaultContent: '',
                render: renderDeleteBtn
            }
        ],
        order: [[0, 'desc']]
    });

    makeEditable(ctx.datatableApi);
});

function renderDeleteBtn(data, type, row) {
    if (type === 'display') {
        return '<a class="delete" onclick="deleteRow(' + row.id + ');">' +
            '<span class="fa fa-remove"></span></a>';
    }
    return data;
}

/*
function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: {
            startDate: $('#startDate').val(),
            endDate: $('#endDate').val(),
            startTime: $('#startTime').val(),
            endTime: $('#endTime').val()
        }
    }).done(function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}*/
