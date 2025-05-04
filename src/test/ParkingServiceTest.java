@ExtendWith(MockitoExtension.class)
class ParkingServiceTest {

    @InjectMocks
    private ParkingServiceImpl parkingService;

    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private ParkingSpaceRepository parkingSpaceRepository;
    @Mock
    private ParkingLotRepository parkingLotRepository;
    @Mock
    private TimeManager timeManager;
    @Mock
    private ParkingLotIncome parkingLotIncome;

    @Test
    void testVehicleLeavingShouldAddIncomeToCorrectLot() {
        // Arrange
        Long vehicleId = 1L;
        Long lotId = 45L;
        int expectedIncome = 30;

        ParkingLot lot = new ParkingLot();
        lot.setParkingLotId(lotId);
        lot.setIncome(100);

        ParkingSpace space = new ParkingSpace();
        space.setParkingSpaceId(2033);
        space.setParkingLot(lot);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(vehicleId);
        vehicle.setLicense("DSC-6697");
        vehicle.setParkingSpace(space);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(parkingLotRepository.findById(lotId)).thenReturn(Optional.of(lot));
        when(timeManager.getCurrentGameTime()).thenReturn(LocalDateTime.now());

        // Act
        parkingService.vehicleLeaving(vehicleId);

        // Assert
        assertEquals(130, lot.getIncome(), "應正確加上收入");
        verify(parkingLotRepository).save(lot); // 確保真的儲存有加錢的那一份
    }
}
