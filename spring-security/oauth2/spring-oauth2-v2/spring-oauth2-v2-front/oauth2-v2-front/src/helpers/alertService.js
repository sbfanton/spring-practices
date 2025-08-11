import Swal from 'sweetalert2';

const showAlert = ({ 
    title, 
    text, 
    icon = 'info', 
    confirmButtonText = 'OK', 
    onConfirm,
    ...swalProps }) => {
  Swal.fire({
    title,
    text,
    icon,
    confirmButtonText,
    ...swalProps
  }).then((result) => {
    if (result.isConfirmed && onConfirm) {
      onConfirm();
    }
  });
};

export default showAlert;