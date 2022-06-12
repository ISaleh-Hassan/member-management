import React, { Component, useEffect } from "react";
import axios from "axios";
import { useState } from "react";

export function Index() {
  const [users, setUsers] = useState([]);
  useEffect(() => {
    axios
      .get("http://localhost:8080/getUsers")
      .then((res) => {
        setUsers(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [users]);

  return <div>{users}</div>;
}

export default Index;
