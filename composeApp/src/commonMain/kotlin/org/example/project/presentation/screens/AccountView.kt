package org.example.project.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import electroniccomponentretail.composeapp.generated.resources.Image
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_dots_vertical
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.SessionData
import org.example.project.checkError
import org.example.project.core.enums.AccountRoleType
import org.example.project.core.enums.AlertType
import org.example.project.data.api.AccountApi
import org.example.project.data.api.AdministratorApi
import org.example.project.data.api.UserAddressApi
import org.example.project.data.api.UserApi
import org.example.project.data.repository.AccountRepository
import org.example.project.data.repository.AdministratorRepository
import org.example.project.data.repository.UserAddressRepository
import org.example.project.data.repository.UserRepository
import org.example.project.domain.model.Account
import org.example.project.domain.model.Administrator
import org.example.project.domain.model.User
import org.example.project.domain.model.UserAddress
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.MenuItem
import org.example.project.presentation.components.SideMenu
import org.example.project.presentation.components.common.*
import org.example.project.presentation.components.dropdown.ExposedDropdownInputField
import org.example.project.presentation.components.dropdown.ExposedDropdownMenuButton
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.isExpanded
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.example.project.presentation.viewmodel.AccountViewModel
import org.example.project.presentation.viewmodel.AdministratorViewModel
import org.example.project.presentation.viewmodel.UserAddressViewModel
import org.example.project.presentation.viewmodel.UserViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

class AccountView : Screen {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope{ Dispatchers.Default}
        val showLoadingOverlay = mutableStateOf(true)
        val totalPage = mutableStateOf(0)
        val currentPage = mutableStateOf(0)
        val showErrorDialog = remember { mutableStateOf(false) }
        val showAddAddressDialog = remember { mutableStateOf(false) }
        val showEditAddressDialog = remember { mutableStateOf(false) }
        val alertType = mutableStateOf(AlertType.Default)
        val selectedTabIndex = mutableStateOf(0)
        val isExpanded = remember { mutableStateOf(true) }
        val currentAccount = mutableStateOf(SessionData.getCurrentAccount() ?: Account())
        val currentUser = mutableStateOf(SessionData.getCurrentUser() ?: User())
        val currentAdmin = mutableStateOf(SessionData.getCurrentAdmin() ?: Administrator())
        val userViewModel = UserViewModel(UserRepository(UserApi()))
        val addressViewModel = UserAddressViewModel(UserAddressRepository(UserAddressApi()))
        val userAddressList = mutableStateOf(emptyList<UserAddress>())
        val accountViewModel = AccountViewModel(AccountRepository(AccountApi()))

        scope.launch {
            when (SessionData.getCurrentAccount()?.accountRole?.name) {
                AccountRoleType.User.name -> {
                    handlerGetUserByAccountId(
                        currentUser = currentUser,
                        showLoadingOverlay = showLoadingOverlay,
                        showErrorDialog = showErrorDialog,
                        alertType = alertType,
                        userViewModel = userViewModel
                    )
                }

                AccountRoleType.Administrator.name -> {
                }
            }
        }

        ColumnBackground(
            rootMaxWidth = rootMaxWidth,
            showLoadingOverlay = showLoadingOverlay
        ) {
            Row() {
                if(rootMaxWidth.value.isExpanded()) {
                    SideMenu(
                        modifier = Modifier.fillMaxHeight(),
                        isExpanded = isExpanded,
                        content = {
                            MenuItem(
                                text = "Profile",
                                icon = Icons.Outlined.AccountCircle,
                                textVisibility = isExpanded.value,
                                onClick = { selectedTabIndex.value = 0 }
                            )
                            if (SessionData.getCurrentAccount()?.accountRole?.name == AccountRoleType.User.name) {
                                MenuItem(
                                    text = "Address",
                                    icon = Icons.Outlined.LocationOn,
                                    textVisibility = isExpanded.value,
                                    onClick = { selectedTabIndex.value = 1 }
                                )
                                MenuItem(
                                    text = "Payment",
                                    icon = Icons.Outlined.AccountBalance,
                                    textVisibility = isExpanded.value,
                                    onClick = { selectedTabIndex.value = 2 }
                                )
                            }
                        }
                    )
                }

                Form(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape
                ) {
                    FlowRow (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(space = Size.Space.S800, alignment = Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.spacedBy(space = Size.Space.S800)
                    ) {
                        when (selectedTabIndex.value) {
                            0 -> {
                                if (navigator != null) {
                                    ProfileTab(
                                        currentAccount = currentAccount,
                                        currentUser = currentUser,
                                        currentAdmin = currentAdmin,
                                        scope = scope,
                                        showLoadingOverlay = showLoadingOverlay,
                                        showErrorDialog = showErrorDialog,
                                        alertType = alertType,
                                        navigator = navigator,
                                        userViewModel = userViewModel,
                                        accountViewModel = accountViewModel
                                    )
                                }
                            }
                            1 -> {
                                scope.launch {
                                    handlerGetUserAddressByUserId(
                                        addressViewModel = addressViewModel,
                                        userViewModel = userViewModel,
                                        currentUser = currentUser,
                                        userAddressList = userAddressList,
                                        showLoadingOverlay = showLoadingOverlay,
                                        showErrorDialog = showErrorDialog,
                                        alertType = alertType
                                    )
                                }
                                AddressTab(
                                    currentAccount = currentAccount,
                                    rootMaxWidth = rootMaxWidth,
                                    showAddAddressDialog = showAddAddressDialog,
                                    showEditAddressDialog = showEditAddressDialog,
                                    currentUser = currentUser,
                                    currentAdmin = currentAdmin,
                                    scope = scope,
                                    addressViewModel = addressViewModel,
                                    userViewModel = userViewModel,
                                    showLoadingOverlay = showLoadingOverlay,
                                    showErrorDialog = showErrorDialog,
                                    alertType = alertType,
                                    userAddressList = userAddressList
                                )
                            }
                            2 -> {
                                if (navigator != null) {
                                    PaymentTab(
                                        currentAccount = currentAccount,
                                        currentUser = currentUser,
                                        currentAdmin = currentAdmin,
                                        scope = scope,
                                        showLoadingOverlay = showLoadingOverlay,
                                        showErrorDialog = showErrorDialog,
                                        alertType = alertType,
                                        navigator = navigator,
                                        userViewModel = userViewModel,
                                        accountViewModel = accountViewModel
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowScope.PaymentTab(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    currentAccount: MutableState<Account>,
    currentUser: MutableState<User>,
    currentAdmin: MutableState<Administrator>,
    scope: CoroutineScope,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    userViewModel: UserViewModel,
    accountViewModel: AccountViewModel,

    ) {
    Column(
        modifier = Modifier.weight(1.5f)

    ) {
        Image(
            modifier = Modifier.aspectRatio(1f)
                .clip(RoundedCornerShape(50)),
            painter = painterResource(Res.drawable.Image),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
    }

    ProfileInfo(
        modifier = Modifier.weight(2f),
        currentAccount = currentAccount,
        currentUser = currentUser,
        currentAdmin = currentAdmin,
        scope = scope,
        showLoadingOverlay = showLoadingOverlay,
        showErrorDialog = showErrorDialog,
        alertType = alertType,
        navigator = navigator,
        userViewModel = userViewModel,
        accountViewModel = accountViewModel
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowScope.AddressTab(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    addressViewModel:UserAddressViewModel,
    userViewModel: UserViewModel,
    rootMaxWidth: MutableState<Int> = remember { mutableStateOf(0) },
    showAddAddressDialog: MutableState<Boolean>,
    showEditAddressDialog: MutableState<Boolean>,
    currentAccount: MutableState<Account>,
    currentUser: MutableState<User>,
    currentAdmin: MutableState<Administrator>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    userAddressList: MutableState<List<UserAddress>>,
    alertType: MutableState<AlertType>
) {
    val newUserAddress = mutableStateOf(UserAddress(user = currentUser.value))
    val updateUserAddress = mutableStateOf(UserAddress(user = currentUser.value))

    AddAndEditAddressDialog(
        title = "Add New Address",
        showAddressDialog = showAddAddressDialog,
        rootMaxWidth = rootMaxWidth,
        userAddress = newUserAddress,
        onConfirmation = {
            scope.launch {
                handlerAddUserAddressByUserId(
                    addressViewModel = addressViewModel,
                    userViewModel = userViewModel,
                    currentUser = currentUser,
                    userAddress = newUserAddress,
                    userAddressList = userAddressList,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType
                )
                handlerGetUserAddressByUserId(
                    addressViewModel = addressViewModel,
                    userViewModel = userViewModel,
                    currentUser = currentUser,
                    userAddressList = userAddressList,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType
                )
                newUserAddress.value = UserAddress(user = currentUser.value)
            }
        }
    )

    AddAndEditAddressDialog(
        title = "Edit Your Address",
        showAddressDialog = showEditAddressDialog,
        rootMaxWidth = rootMaxWidth,
        userAddress = updateUserAddress,
        onConfirmation = {
            scope.launch {
                handlerUpdateUserAddressByUserId(
                    addressViewModel = addressViewModel,
                    userViewModel = userViewModel,
                    userAddress = updateUserAddress,
                    userAddressList = userAddressList,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType
                )

                handlerGetUserAddressByUserId(
                    addressViewModel = addressViewModel,
                    userViewModel = userViewModel,
                    currentUser = currentUser,
                    userAddressList = userAddressList,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType
                )
                updateUserAddress.value = UserAddress(user = currentUser.value)
            }
        }
    )
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodyText(
                text = "Address",
                style = Typography.Style.Heading5
            )
            CustomButton(
                text = "Add Address",
                icon = Icons.Outlined.Add,
                isIconFirst = true,
                onClick = { showAddAddressDialog.value = true }
            )
        }
        Divider(Modifier.padding(vertical = Size.Space.S600))
        Column(
            verticalArrangement = Arrangement.spacedBy(Size.Space.S600),
        ) {
            userAddressList.value.forEach { address ->
                AddressItem(
                    currentAccount = currentAccount,
                    rootMaxWidth = rootMaxWidth,
                    showEditAddressDialog = showEditAddressDialog,
                    address = address,
                    onEdit = {
                        updateUserAddress.value = address
                        showEditAddressDialog.value = true
                    },
                    onDelete = {
                        scope.launch {
                            handlerDeleteUserAddressByUserId(
                                addressViewModel = addressViewModel,
                                userViewModel = userViewModel,
                                userAddress = updateUserAddress,
                                userAddressList = userAddressList,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType
                            )
                        }
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowScope.ProfileTab(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    currentAccount: MutableState<Account>,
    currentUser: MutableState<User>,
    currentAdmin: MutableState<Administrator>,
    scope: CoroutineScope,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    userViewModel: UserViewModel,
    accountViewModel: AccountViewModel,

    ) {
    Column(
        modifier = Modifier.weight(1f)
    ) {
        Image(
            modifier = Modifier.aspectRatio(1f)
                .clip(RoundedCornerShape(50)),
            painter = painterResource(Res.drawable.Image),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
    }

    ProfileInfo(
        modifier = Modifier.weight(1f),
        currentAccount = currentAccount,
        currentUser = currentUser,
        currentAdmin = currentAdmin,
        scope = scope,
        showLoadingOverlay = showLoadingOverlay,
        showErrorDialog = showErrorDialog,
        alertType = alertType,
        navigator = navigator,
        userViewModel = userViewModel,
        accountViewModel = accountViewModel
    )
}

@Composable
fun ProfileInfo(
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
    navigator: Navigator,
    currentAccount: MutableState<Account>,
    currentUser: MutableState<User>,
    currentAdmin: MutableState<Administrator>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    userViewModel: UserViewModel,
    accountViewModel: AccountViewModel,
    ) {
    Form(
        modifier = modifier.fillMaxWidth(),
        padding = 0.dp,
        color = Themes.Light.primaryLayout.copy(border = Color.Transparent)
    ) {
        BodyText(
            text = "Profile",
            style = Typography.Style.Heading4
        )
        when (SessionData.getCurrentAccount()?.accountRole?.name) {
            AccountRoleType.User.name -> {

                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Email",
                    value = currentAccount.value.email ?: "",
                    onValueChange = {
                        currentAccount.value = currentAccount.value.copy(email = it)
                    },
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Full name",
                    value = currentUser.value.fullName ?: "",
                    onValueChange = {
                        currentUser.value = currentUser.value.copy(fullName = it)
                    },
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Phone number",
                    value = currentAccount.value.phoneNumber ?: "",
                    onValueChange = {
                        currentAccount.value = currentAccount.value.copy(phoneNumber = it)
                    },
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S200)
                ) {
                    CustomButton(
                        modifier = Modifier.weight(1f),
                        text = "Cancel",
                        color = Themes.Light.subtleButton,
                        onClick = {
                            navigator.pop()
                        }
                    )
                    CustomButton(
                        modifier = Modifier.weight(1f),
                        text = "Save",
                        onClick = {
                            scope.launch {
                                handlerUpdateUser(
                                    currentUser = currentUser,
                                    showLoadingOverlay = showLoadingOverlay,
                                    showErrorDialog = showErrorDialog,
                                    alertType = alertType,
                                    userViewModel = userViewModel
                                )
                                handlerUpdateAccount(
                                    accountViewModel = accountViewModel,
                                    currentAccount = currentAccount,
                                    showLoadingOverlay = showLoadingOverlay,
                                    showErrorDialog = showErrorDialog,
                                    alertType = alertType
                                )
                                handlerGetUserByAccountId(
                                    currentUser = currentUser,
                                    showLoadingOverlay = showLoadingOverlay,
                                    showErrorDialog = showErrorDialog,
                                    alertType = alertType,
                                    userViewModel = userViewModel
                                )
                            }
                        }
                    )
                }
            }

            AccountRoleType.Administrator.name -> {
                scope.launch {
                    handlerGetAdminByAccountId(
                        currentAdmin = currentAdmin,
                        showLoadingOverlay = showLoadingOverlay,
                        showErrorDialog = showErrorDialog,
                        alertType = alertType
                    )
                }
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Username",
                    value = currentAccount.value.username ?: "",
                    enabled = false,
                    onValueChange = {},
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Job position",
                    enabled = false,
                    value = currentAdmin.value.jobPosition?.name ?: "",
                    onValueChange = {},
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Access level",
                    enabled = false,
                    value = currentAdmin.value.accessLevel?.name ?: "",
                    onValueChange = {},
                )
            }
        }


    }
}

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    rootMaxWidth: MutableState<Int> = remember { mutableStateOf(0) },
    showEditAddressDialog: MutableState<Boolean> = mutableStateOf(false),
    currentAccount: MutableState<Account> = mutableStateOf(SessionData.getCurrentAccount()?:Account()),
    address: UserAddress?,
    color: ButtonColor = Themes.Light.subtleButton,
    showButton: Boolean = true,
    onEdit:  ()->Unit = {},
    onDelete:  ()->Unit = {},
) {
    Form(
        modifier = modifier.fillMaxWidth(),
        space = Size.Space.S300
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            BodyText(
                text = address?.name ?: "",
                style = Typography.Style.BodyText.merge(fontWeight = FontWeight.SemiBold, color = color.secondaryText?: Color.Unspecified)
            )
            if(address?.isDefault == true) {
                Tag(
                    text = "Default",
                    color = Themes.Light.primaryLayout
                )
            }
        }
        Divider()
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(Size.Space.S200)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S400),
                ) {
                    BodyText(
                        text = address?.user?.fullName ?: ""
                    )
                    BodyText(
                        text = currentAccount.value.phoneNumber ?: "",
                        style = Typography.Style.BodySmall.merge(color = color.secondaryText?: Color.Unspecified)

                    )
                }
                BodyText(
                    text = address?.street ?: "",
                    style = Typography.Style.BodySmall.merge(color = color.secondaryText?: Color.Unspecified)
                )
                BodyText(
                    text = listOf(address?.ward, address?.commune, address?.district, address?.city, address?.province)
                        .filterNotNull()
                        .joinToString(separator = ", "),
                    style = Typography.Style.BodySmall.merge(color = color.secondaryText?: Color.Unspecified)
                )
            }
            if (rootMaxWidth.value.isExpanded() && showButton) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S300)
                ) {
                    IconButton(
                        onClick = {
                            showEditAddressDialog.value = true
                            onEdit()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = {
                            onDelete()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    }
                }
            } else {
                if(showButton) {
                    ExposedDropdownMenuButton(
                        icon = vectorResource(Res.drawable.ic_dots_vertical),
                        content = {}
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddAndEditAddressDialog(
    title: String,
    showAddressDialog: MutableState<Boolean>,
    rootMaxWidth: MutableState<Int>,
    userAddress: MutableState<UserAddress>,
    onConfirmation: () -> Unit,
    ) {
    AlertDialog(
        title = title,
        showDialog = showAddressDialog,
        rootMaxWidth = rootMaxWidth,
        message = null,
        onConfirmation = onConfirmation,
        content = {
            Form(
                modifier = Modifier.fillMaxWidth(),
                color = Themes.Light.primaryLayout.copy(border = Color.Transparent),
                padding = 0.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S400)
                ) {
                    InputField(
                        modifier = Modifier.weight(1f),
                        value = userAddress.value.name?:"",
                        onValueChange = {
                            userAddress.value = userAddress.value.copy(name = it)
                        },
                        placeHolder = "Name",
                        label = "Name"
                    )
                    Checkbox(
                        modifier = Modifier.weight(1f),
                        text = "Default",
                        checked = mutableStateOf(userAddress.value.isDefault ?: false)
                    )
                }
                InputField(
                    modifier = Modifier.fillMaxWidth(1f),
                    value = userAddress.value.street?:"",
                    onValueChange = {
                        userAddress.value = userAddress.value.copy(street = it)
                    },
                    placeHolder = "Street Address",
                    label = "Street Address"
                )
                ExposedDropdownInputField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Province",
                    label = "Province",
                    textFieldValue = mutableStateOf(userAddress.value.province?:""),
                    onValueChange = {
                        userAddress.value = userAddress.value.copy(province = it)
                    }
                )
                ExposedDropdownInputField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "District",
                    label = "District",
                    textFieldValue = mutableStateOf(userAddress.value.district?:""),
                    onValueChange = {
                        userAddress.value = userAddress.value.copy(district = it)
                    }
                )
                ExposedDropdownInputField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Ward",
                    label = "Ward",
                    textFieldValue = mutableStateOf(userAddress.value.ward?:""),
                    onValueChange = {
                        userAddress.value = userAddress.value.copy(ward = it)
                    }
                )
            }
        }
    )
}

suspend fun handlerUpdateAccount(
    accountViewModel: AccountViewModel,
    currentAccount: MutableState<Account>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            SessionData.getCurrentAccount()?.id?.let { accountViewModel.updateAccount(it, currentAccount.value) }
            if (accountViewModel.account.value != null) {
                SessionData.setCurrentAccount(accountViewModel.updatedAccount.value!!)
                currentAccount.value = SessionData.getCurrentAccount()!!
            }
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = accountViewModel.operationStatus,
    )
}

suspend fun handlerGetUserByAccountId(
    currentUser: MutableState<User>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    userViewModel: UserViewModel,
    ) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            SessionData.getCurrentAccount()?.id?.let { userViewModel.getUserByAccountId(it) }
            if (userViewModel.user.value != null) {
                SessionData.setCurrenUser(userViewModel.user.value!!)
                currentUser.value = SessionData.getCurrentUser()!!
            }
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = userViewModel.operationStatus,
    )
}

suspend fun handlerUpdateUser(
    currentUser: MutableState<User>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    userViewModel: UserViewModel,
    ) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            userViewModel.updateUser(currentUser.value.id ?: 0, currentUser.value)
            if(userViewModel.updatedUser.value!=null) {
                SessionData.setCurrenUser(userViewModel.updatedUser.value)
            }
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = userViewModel.operationStatus,
    )
}

suspend fun handlerGetUserAddressByUserId(
    addressViewModel:UserAddressViewModel,
    userViewModel: UserViewModel,
    currentUser: MutableState<User>,
    userAddressList: MutableState<List<UserAddress>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            currentUser.value.id?.let { addressViewModel.getUserAddresssByUserId(0, it) }
            userAddressList.value = addressViewModel.userAddresssList.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = userViewModel.operationStatus,
    )
}

suspend fun handlerAddUserAddressByUserId(
    addressViewModel:UserAddressViewModel,
    userViewModel: UserViewModel,
    currentUser: MutableState<User>,
    userAddress: MutableState<UserAddress>,
    userAddressList: MutableState<List<UserAddress>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            handlerGetUserAddressByUserId(
                addressViewModel = addressViewModel,
                userViewModel = userViewModel,
                currentUser = currentUser,
                userAddressList = userAddressList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
            if(userAddressList.value.size<20) {
                addressViewModel.createUserAddress(userAddress.value)
                userAddressList.value = addressViewModel.userAddresssList.value
            }
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = userViewModel.operationStatus,
    )
}

suspend fun handlerUpdateUserAddressByUserId(
    addressViewModel: UserAddressViewModel,
    userViewModel: UserViewModel,
    userAddress: MutableState<UserAddress>,
    userAddressList: MutableState<List<UserAddress>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            addressViewModel.updateUserAddress(userAddress.value.id ?: 0, userAddress.value)
            if (addressViewModel.updatedUserAddress.value != null) {
                userAddress.value = addressViewModel.updatedUserAddress.value ?: UserAddress()
            }
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = userViewModel.operationStatus,
    )
}

suspend fun handlerDeleteUserAddressByUserId(
    addressViewModel: UserAddressViewModel,
    userViewModel: UserViewModel,
    userAddress: MutableState<UserAddress>,
    userAddressList: MutableState<List<UserAddress>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            addressViewModel.deleteUserAddress(userAddress.value.id ?: 0)
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = userViewModel.operationStatus,
    )
}

suspend fun handlerGetAdminByAccountId(
    currentAdmin: MutableState<Administrator>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    val adminViewModel = AdministratorViewModel(AdministratorRepository(AdministratorApi()))
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            SessionData.getCurrentAccount()?.id?.let { adminViewModel.getAdministratorByAccountId(it) }
            if (adminViewModel.administrator.value != null) {
                currentAdmin.value = adminViewModel.administrator.value!!
                SessionData.setCurrentAdmin(adminViewModel.administrator.value!!)
                currentAdmin.value = SessionData.getCurrentAdmin()!!
            }
        }
    )

    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = adminViewModel.operationStatus,
    )
}