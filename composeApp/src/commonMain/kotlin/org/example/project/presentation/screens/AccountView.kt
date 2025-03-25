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
import org.example.project.data.api.AdministratorApi
import org.example.project.data.api.UserApi
import org.example.project.data.repository.AdministratorRepository
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
import org.example.project.presentation.viewmodel.AdministratorViewModel
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
        val currentAccount = SessionData.getCurrentAccount()
        val currentUser = mutableStateOf(User())
        val currentAdmin = mutableStateOf(Administrator())

        ColumnBackground(
            rootMaxWidth = rootMaxWidth
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
                                ProfileTab(
                                    currentAccount = currentAccount,
                                    currentUser = currentUser,
                                    currentAdmin = currentAdmin,
                                    scope = scope,
                                    showLoadingOverlay = showLoadingOverlay,
                                    showErrorDialog = showErrorDialog,
                                    alertType = alertType
                                )
                            }
                            1 -> {
                                AddressTab(
                                    currentAccount = currentAccount,
                                    rootMaxWidth = rootMaxWidth,
                                    showAddAddressDialog = showAddAddressDialog,
                                    showEditAddressDialog = showEditAddressDialog,
                                    currentUser = currentUser,
                                    currentAdmin = currentAdmin
                                )
                            }
                            2 -> {
                                PaymentTab(
                                    currentAccount = currentAccount,
                                    currentUser = currentUser,
                                    currentAdmin = currentAdmin,
                                    scope = scope,
                                    showLoadingOverlay = showLoadingOverlay,
                                    showErrorDialog = showErrorDialog,
                                    alertType = alertType
                                )
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
    currentAccount: Account?,
    currentUser: MutableState<User>,
    currentAdmin: MutableState<Administrator>,
    scope: CoroutineScope,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
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
        alertType = alertType
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowScope.AddressTab(
    modifier: Modifier = Modifier,
    rootMaxWidth: MutableState<Int> = remember { mutableStateOf(0) },
    showAddAddressDialog: MutableState<Boolean>,
    showEditAddressDialog: MutableState<Boolean>,
    currentAccount: Account?,
    currentUser: MutableState<User>,
    currentAdmin: MutableState<Administrator>
) {
    AddAndEditAddressDialog(
        title = "Add New Address",
        showAddressDialog = showAddAddressDialog,
        rootMaxWidth = rootMaxWidth
    )
    AddAndEditAddressDialog(
        title = "Edit Your Address",
        showAddressDialog = showEditAddressDialog,
        rootMaxWidth = rootMaxWidth
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
            AddressItem(
                currentAccount = currentAccount,
                rootMaxWidth = rootMaxWidth,
                showEditAddressDialog = showEditAddressDialog,
            )
            AddressItem(
                currentAccount = currentAccount,
                rootMaxWidth = rootMaxWidth,
                showEditAddressDialog = showEditAddressDialog,
            )
            AddressItem(
                currentAccount = currentAccount,
                rootMaxWidth = rootMaxWidth,
                showEditAddressDialog = showEditAddressDialog,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowScope.ProfileTab(
    modifier: Modifier = Modifier,
    currentAccount: Account?,
    currentUser: MutableState<User>,
    currentAdmin: MutableState<Administrator>,
    scope: CoroutineScope,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
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
        alertType = alertType
    )
}

@Composable
fun ProfileInfo(
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
    currentAccount: Account?,
    currentUser: MutableState<User>,
    currentAdmin: MutableState<Administrator>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
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
                scope.launch {
                    handlerGetUserByAccountId(
                        currentUser = currentUser,
                        showLoadingOverlay = showLoadingOverlay,
                        showErrorDialog = showErrorDialog,
                        alertType = alertType
                    )
                }
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Username",
                    value = currentAccount?.username ?: "",
                    enabled = false,
                    onValueChange = {},
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Fullname",
                    value = currentAccount?.username ?: "",
                    enabled = false,
                    onValueChange = {},
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Email",
                    value = currentAccount?.email ?: "",
                    onValueChange = {},
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Phone number",
                    value = currentAccount?.phoneNumber ?: "",
                    onValueChange = {},
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S200)
                ) {
                    CustomButton(
                        modifier = Modifier.weight(1f),
                        text = "Cancel",
                        color = Themes.Light.subtleButton,
                        onClick = {}
                    )
                    CustomButton(
                        modifier = Modifier.weight(1f),
                        text = "Save",
                        onClick = {}
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
                    value = currentAccount?.username ?: "",
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
    showEditAddressDialog: MutableState<Boolean>,
    currentAccount: Account?,
    address: UserAddress? = UserAddress(
        /*name = "Home",
        street = "23 Nguyen Trai",
        ward = "Phuong",
        district = "Quan Ha Dong",
        city = "Ha Noi",
        isDefault = true*/
    ),
    color: ButtonColor = Themes.Light.subtleButton,
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
                        text = currentAccount?.username ?: currentAccount?.email ?: ""
                    )
                    BodyText(
                        text = currentAccount?.phoneNumber ?: "",
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
                        .joinToString(separator = " ,"),
                    style = Typography.Style.BodySmall.merge(color = color.secondaryText?: Color.Unspecified)
                )
            }
            if (rootMaxWidth.value.isExpanded()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S300)
                ) {
                    IconButton(
                        onClick = {
                            showEditAddressDialog.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = null
                        )
                    }

                    ExposedDropdownMenuButton(
                        icon = Icons.Outlined.Delete,
                        content = {}
                    )
                }
            } else {
                ExposedDropdownMenuButton(
                    icon = vectorResource(Res.drawable.ic_dots_vertical),
                    content = {}
                )
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
) {
    AlertDialog(
        title = title,
        showDialog = showAddressDialog,
        rootMaxWidth = rootMaxWidth,
        message = null,
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
                        value = "",
                        onValueChange = {  },
                        placeHolder = "Name"
                    )
                    Checkbox(
                        modifier = Modifier.weight(1f),
                        text = "Default"
                    )
                }
                InputField(
                    modifier = Modifier.fillMaxWidth(1f),
                    value = "",
                    onValueChange = {  },
                    placeHolder = "Street Address"
                )
                ExposedDropdownInputField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Province"
                )
                ExposedDropdownInputField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "District"
                )
                ExposedDropdownInputField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Ward"
                )

            }
        }
    )
}

fun handlerUpdateProfile(
    currentUser: MutableState<User>,
    currentAdmin: MutableState<Administrator>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    when (SessionData.getCurrentAccount()?.accountRole?.name) {
        AccountRoleType.User.name -> {

        }

        AccountRoleType.Administrator.name -> {

        }
    }
}

suspend fun handlerGetUserByAccountId(
    currentUser: MutableState<User>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    val userViewModel = UserViewModel(UserRepository(UserApi()))
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

suspend fun handlerUpdateUserByAccountId(
    currentUser: MutableState<User>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    val userViewModel = UserViewModel(UserRepository(UserApi()))
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            userViewModel.updateUser(currentUser.value.id ?: 0, currentUser.value)

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