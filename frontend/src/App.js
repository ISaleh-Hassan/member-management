import logo from "./logo.svg";
import "./App.css";
import Index from "./components";
import Login from "./components/admin/Login";
import React from "react";
function App() {
  return (
    <div className="App">
      <header className="App-header"></header>
      <Login></Login>
    </div>
  );
}

export default App;
