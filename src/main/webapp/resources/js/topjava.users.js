const userAjaxUrl = "admin/users/";

const ctx = {
    ajaxUrl: userAjaxUrl
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {"data": "name"},
                {"data": "email"},
                {"data": "roles"},
                {"data": "enabled"},
                {"data": "registered"},
                {"defaultContent": "Edit", "orderable": false},
                {"defaultContent": "Delete", "orderable": false}
            ],
            "order": [[0, "asc"]]
        })
    );
});

function enable(checkbox, id) {
    var enabled = checkbox.is(':checked');
    $.ajax({
        url: ctx.ajaxUrl + id,
        type: 'PATCH',
        data: {enabled: enabled}
    }).done(function () {
        checkbox.closest('tr').toggleClass('disabled', !enabled);
        successNoty(enabled ? 'Enabled' : 'Disabled');
    }).fail(function () {
        checkbox.prop('checked', !enabled);
    });
}