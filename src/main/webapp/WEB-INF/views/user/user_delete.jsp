<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="userEditForm" name="userEditForm" ng-submit="save()">
    <input type="hidden" name="id" ng-model="editedUser.id">
    <div class="modal-body">
        Are you sure that you want to delete user {{userToDelete.userName}} ?
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" ng-click="delete ()">OK</button>
        <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
    </div>
</form>
