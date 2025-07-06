import { useLoading } from '../context/LoadingContext';
import ClipLoader from "react-spinners/ClipLoader";

const Loader = () => {
  const { isLoading } = useLoading();

  if (!isLoading) return null;

  return (
      <div style={{
        position: "fixed",
        top: 0, left: 0, right: 0, bottom: 0,
        backgroundColor: "rgba(0,0,0,0)",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        zIndex: 9999
      }}>
        <ClipLoader color="#0e56ff" loading={isLoading} size={60} />
      </div>
  );
};

export default Loader;
