import React from "react";
import "./App.css";
import Login from "./components/userAccount/Login";
import ProtectedRoute from "./routes/ProtectedRoute"
import Registration from "./components/userAccount/Registration";
import RegistrationInformation from "./components/userAccount/RegistrationInformation";
import MemberPayments from "./components/member/MemberPayments";
import MemberAdministration from "./components/member/MemberAdministration";
import UserAccountService from "./services/UserAccountService";
import { useGlobalState } from "./state";
import { Routes, Route, Link } from "react-router-dom";
import Navbar from "./components/header/Navbar";

//This map is used to map the component name that will be shown in the Navbar with the actual route
var routeMap = {
  "Member payment management": "member-payment-management",
  "Member adminstration": "member-administration"
}

// All those mapping are not working yet. But they do exist in the navbar
// An exception is Logout function which is working fine.
const settings = ['Profile', 'Account', 'Dashboard', 'Logout'];

function App() {
  const showRegistrationInformation = useGlobalState("showRegistrationInformation");
  const token = localStorage.getItem("jwtToken");
  const [isAuthorized, setIsAuthorized] = React.useState(false)
  //TODO: I need to check how to make the endpoint call from here. The endpoint is working but not here
  React.useEffect(() => {
    if(token !== null && token !== undefined){
      const x = UserAccountService.validateToken(token);
      x.then(res => setIsAuthorized(res))
    }
  },[])
  return (
    <div className="App">
      <Navbar routeMap={routeMap} settings={settings} isAuthorized={isAuthorized}/>
      <Routes>
        <Route path="/" element={
          <ProtectedRoute>
            <MemberAdministration />
          </ProtectedRoute>
        } />
        <Route path="registration" element={<Registration isAuthorized={isAuthorized} />} />
        <Route path="member-payment-management" element={
          <ProtectedRoute>
            <MemberPayments />
          </ProtectedRoute>
        } />
        <Route path="member-administration" element={
          <ProtectedRoute>
            <MemberAdministration />
          </ProtectedRoute>
        } />
        <Route path="login" element={<Login isAuthorized={isAuthorized} />} />
        <Route path="registration" element={<Registration isAuthorized={isAuthorized} />} />
        {showRegistrationInformation[0] === true ? (<Route path="registration-information" element={<RegistrationInformation />} />) : null}
      </Routes>
    </div>
  );
}

export default App;
