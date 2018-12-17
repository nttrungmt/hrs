//$(document).ready(function() {
function validateID(value) {
    var reID = /^\d+$/;
    return reID.test(value);
}

function validateForm(event)
{
	event.preventDefault();
    // Validate WarehouseID
    var warehouseID = document.tpcc_inputform.WarehouseID.value;
    if (validateID(warehouseID)) { } else {
        alert("Please enter a valid integer for Warehouse ID");
        document.tpcc_inputform.WarehouseID.focus();
        return false;
    }

    // Validate DistrictID
    var districtID = document.tpcc_inputform.DistrictID.value;
    if (validateID(districtID)) { } else {
        alert("Please enter a valid integer for District ID");
        document.tpcc_inputform.DistrictID.focus();
        return false;
    }

    // Validate CustomerID
    var customerID = document.tpcc_inputform.CustomerID.value;
    if (validateID(customerID)) { } else {
        alert("Please enter a valid integer for Customer ID");
        document.tpcc_inputform.CustomerID.focus();
        return false;
    }

    document.tpcc_inputform.submit();// fire submit event
}
//});