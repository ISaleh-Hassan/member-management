import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import axios from "axios";


const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 600,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

export default function FileUploader() {
  const [uploadInfoModal, setUploadInfoModal] = React.useState(false);
  const [uploadModal, setUploadModal] = React.useState(false);
  const [selectedFile, setSelectedFile] = React.useState();

  React.useEffect(() => {
    if (selectedFile) {
      axios.post(process.env.REACT_APP_BASE_SERVER_URL + "support/validate-csv", selectedFile, {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      })
        .catch((ignore) => {
          alert("The uploaded file is not valid. Make sure to download the english version from Swedbank and try again!")
          setUploadModal(false)
          setUploadInfoModal(true)
        });
    }
  }, [selectedFile])

  const handleOpenUploadInfoModal = () => {
    setUploadModal(false)
    setUploadInfoModal(true);
  }

  const handleCloseModal = () => {
    setUploadModal(false);
    setUploadInfoModal(false);
    setSelectedFile(null)
  }

  const handleSelectFile = event => {
    setUploadInfoModal(false)
    setUploadModal(true)
    setSelectedFile({ selectedFile: event.target.files[0] });
  };



  function confirmTransactionsFile() {
    axios.post(process.env.REACT_APP_BASE_SERVER_URL + "payments", selectedFile, {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    }).then(res => {
      if (res.status === 200) {
        setUploadInfoModal(false)
        setUploadModal(false)
        setSelectedFile(null)
      } else {
        alert("Something went wrong while confirming the transactions file, please try again")
      }
    })
      .catch((err) => {
        alert("Something went wrong while confirming the transactions file, please try again")
      });
  }

  return (
    <div>
      <Button variant="contained" component="label" onClick={handleOpenUploadInfoModal} >Upload new Swedbank transactions</Button>
      {uploadInfoModal ?
        <Modal
          open={uploadInfoModal}
          onClose={handleCloseModal}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >

          <Box sx={style}>
            <h2 id="modal-modal-title" variant="h6" component="h2">
              Upload new Swedbank transactions
            </h2>
            <Typography id="modal-modal-description" sx={{ mt: 2 }}>
              Make sure to upload the English version of Swedbank transactions
            </Typography>
            <Button variant="outlined" component="label" onClick={handleCloseModal} sx={{ mt: 2 }}>Cancel</Button>
            <Button variant="contained" component="label" sx={{ ml: 2, mt: 2 }}>
              <input type="file" hidden onChange={handleSelectFile} />
              Upload file
            </Button>
          </Box>
        </Modal>
        : uploadModal ?
          <Modal
            open={uploadModal}
            onClose={handleCloseModal}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
          >
            <Box sx={style}>
              <h2 id="modal-modal-title" variant="h6" component="h2">
                File Details
              </h2>
              <p>File Name: {selectedFile.selectedFile.name}</p>
              <p>File Type: {selectedFile.selectedFile.type}</p>
              <Button variant="outlined" component="label" onClick={handleCloseModal} sx={{ mt: 2 }}>Cancel</Button>
              <Button variant="contained" component="label" sx={{ ml: 2, mt: 2 }} onClick={confirmTransactionsFile}>Confirm</Button>
            </Box>
          </Modal>
          : null
      }
    </div>
  );
}
