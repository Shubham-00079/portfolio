import CollectionPage from "../../components/CollectionPage";
import { catalog } from "./catalog";

export default function AdminExperience() {
  return <CollectionPage definition={catalog.experience} />;
}
