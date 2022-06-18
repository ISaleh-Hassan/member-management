import React, { Component, Fragment, useEffect } from "react";
import axios from "axios";
import { useState } from "react";

export function Index() {
  const [users, setUsers] = useState([]);
  // useEffect(() => {
  //   axios
  //     .get("http://localhost:8080/getUsers")
  //     .then((res) => {
  //       setUsers(res.data);
  //     })
  //     .catch((err) => {
  //       console.log(err);
  //     });
  // }, [users]);
 
   const [selectedFile, setSelectedFile] = useState();

  // On file select (from the pop up)
 const onFileChange = (event) => {
    // Update the state
    setSelectedFile({ selectedFile: event.target.files[0] });
  };
  const onFileUpload = () => {
    // Create an object of formData
    let formData = new FormData();
    formData.append("file", selectedFile);


    axios.post("http://localhost:8083/api/v1/payments", selectedFile, {
      headers:{
        "Content-Type":"multipart/form-data"
      }
    });
  };

  // File content to be displayed after
  // file upload is complete
  const fileData = () => {
    if (selectedFile) {
      return (
        <div>
          <h2>File Details:</h2>
          <p>File Name: {selectedFile.selectedFile.name}</p>
          <p>File Type: {selectedFile.selectedFile.type}</p>
          <p>
            Last Modified:{" "}
            {selectedFile.selectedFile.lastModified}
          </p>
        </div>
      );
    } else {
      return (
        <div>
          <br />
          <h4>Choose before Pressing the Upload button</h4>
        </div>
      );
    }
  }

  return (
    <Fragment>
      <div>{users}</div>
      <h3>File Upload using React!</h3>
      <div>
        <input type="file" onChange={onFileChange} />
        <button onClick={onFileUpload}>Upload!</button>
      </div>
      {fileData()}
    </Fragment>
  );
}
export default Index;
