import CollectionPage from "../../components/CollectionPage";
import { catalog } from "./catalog";

export default function AdminProjects() {
  return <CollectionPage definition={catalog.projects} />;
}
