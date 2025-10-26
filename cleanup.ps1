# PowerShell script to remove duplicate FXML files
$filesToRemove = @(
    "src\main\resources\banking\ui\CustomerCreationPage.fxml",
    "src\main\resources\banking\ui\TransactionAccountPage.fxml",
    "src\main\resources\banking\ui\SavingsAccountPage.fxml"
)

foreach ($file in $filesToRemove) {
    if (Test-Path $file) {
        Remove-Item -Path $file -Force
        Write-Host "Removed: $file"
    } else {
        Write-Host "Not found (already removed): $file"
    }
}

Write-Host "Cleanup complete!"
