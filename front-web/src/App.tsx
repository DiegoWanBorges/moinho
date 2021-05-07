import 'bootstrap/dist/css/bootstrap.min.css';
import "./app.scss"
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Routes from "./core/routes/Routes";

function App() {
  return (
    <>
      <ToastContainer/>
      <Routes/>
    </>
  );
}

export default App;
