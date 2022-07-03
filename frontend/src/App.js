import React from "react";
import "./App.css";
import Login from "./components/admin/index"
import FileUploader from "./components/fileUploader/FileUploader";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link
} from "react-router-dom";
import MemeberPayments from "./components/member/MemberPayments";
function App() {
  return (
    <div className="App">
    <Router>
      <div>
        <nav>
          <ul>
            <li>
              <Link to="/">Login</Link>
            </li>
            <li>
              <Link to="/member-payment-management">Member payments</Link>
            </li>
          </ul>
        </nav>

        <Routes>
          <Route exact path="/" element={ <Login />} />
          <Route path="/member-payment-management" element={ <MemeberPayments />} />
        </Routes>
      </div>
    </Router>
    </div>
  );
}

export default App;
